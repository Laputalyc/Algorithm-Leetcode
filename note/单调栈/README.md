# 单调栈结构
## 1.先导问题
一个数组，要求其中的每一个数左边比它大且离它最近，右边比它大离它最近的数。要求在时间复杂度O(n)内完成。首先我们会考虑采用遍历的方法来求解这题，但是时间复杂度为O(n^2)。
## 2.单调栈结构
准备一个栈，保证栈底到栈顶是由大到小的。例如考虑一组数543653。首先是5，由于栈中没有任何数需要考虑，所以5直接进栈，然后是4,4也直接进栈，然后是3,3也直接进栈，然后是6，这时候6不能直接进栈。所以要弹栈，**3弹出，3是因为6弹出的，所以3右边的离它最近的最大值为6,3的下面是4所以3的左边离它最近的是4，同理4也是如此，5的右边的最大值也是6，但是5的底下是null了，所以5的左边离它最近且比它大的值没有**，到了这一步后6再进栈，然后5进栈，3进栈，然后没有数了。这时候再来处理栈中剩余的数，**3,5,6都不因任何数而弹出，所以它们右边离他们最近的值都是null，左边的话同上**。  
【特殊情况】，当数组中出现相等的数时该怎么处理？把相等的数的索引压在一起即可。
## 3.应用举例
### 3.1 构造数组的MaxTree  
#### 3.1.1 题目描述

定义二叉树结点如下：

```
public class Node {
	public int value;
	public Node left;
	public Node right;
	public Node(int data) {
		this.value = data;
	}
}
```

一个数组的MaxTree定义如下。

* 数组中必须没有重复元素。
* MaxTree是一颗二叉树，数组的每一个值对应一个二叉树结点。
* 包括MaxTree树在内且在其中的每一颗子树上，值最大的结点的都是树的头。
* 要求时间复杂度为O(N)、额外空间复杂度为O(N)。

#### 3.1.2 思路0

采用堆排序的思想，可以形成一个平衡的符合条件的MaxTree。 
 
#### 3.1.2 思路1

即单调栈的思想。首先得到数组中所有元素左边和右边离它最近的比它大的元素的信息。如果一个元素左边无最大值，右边也无最大值，那么这个元素为头结点；如果一个元素只有一边又较大值，则该元素挂在较大值后面；如果一个元素左边和右边均有较大值，则挂在较小的那个最大值下面。

对于本题来说只是一个简单的单调栈的应用，但从题解上来看，采用堆排序的思想是一种更优解。

### 3.2 求最大子矩阵的大小
#### 3.2.1 题目描述 
给定一个整形矩阵map，其中的值只有0和1两种，求其中全是1的所有矩阵区域中，最大矩形区域为1的数量。

```
例如：  
1 1 1 0  
其中，最大的矩形区域有3个1，所以返回3。
```

```
再如：  
1 0 1 1  
1 1 1 1  
1 1 1 0  
其中，最大的矩形区域有6个1，所以返回6。
```

#### 3.2.2 思路 

首先介绍一个**数组与直方图**的概念：

如图所示：将数组`[4, 3, 2, 5]`等效成如图所示的直方图，用小正方形的数量代表各索引出的数字大小。先如要求在这个直方图中构成的最大矩形的面积（每个小正方形的面积为1）该怎么求呢？
这个其实就很好想了，我们首先以索引0所在的列往两边扩，能扩出的最大矩形的面积计算出即来，对于它来说它往右扩的索引为1，往左扩的索引为-1，所以最大矩形面积为1*4=4,同理可得其它列的情况，而这就可以用单调栈来处理，但这个单调栈是找两边的较小值。所以只需要变通一下限制单调栈是自底向上由小到大即可。

#### 3.2.3 代码实现

```
public class MaxSubRec {
    /**
     * 根据直方图的思想求最大的子矩形
     * @param height 直方图
     * @return 最大子矩形中含有1的数量
     */
    public static int maxRecFromBottom(int[] height) {
        if(height==null || height.length==0) {
            return 0;
        }
        int max = 0;
        Stack<Integer> stack = new Stack<>();//单调栈，里面放的是元素的索引
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i]<=height[stack.peek()]) {//栈中元素非空，并且当前索引直方图的高度小于栈顶位置直方图的高度，说明往右扩不了了。
                //那么此时索引i就是有边界的位置
                //左边界的位置需要看栈中的下一个元素
                int j = stack.pop();
                int k = stack.isEmpty()?-1:stack.peek();
                //刚弹出的索引出的所能扩出的矩形的1的个数为
                int temp = (i-k-1)*height[j];
                max = Math.max(temp, max);
            }
            stack.push(i);
        }
        /*
         * 当上面一系列操作都完成之后再处理栈中剩余的元素
         *      * 这个时候栈中所有元素的右边界都是height.length;
         *      * 栈中所有元素的左边界同上处理
         */
        while (!stack.isEmpty()) {
            int j = stack.pop();
            //左边界
            int k = stack.isEmpty()?-1:stack.peek();
            int temp = (height.length-k-1)*height[j];
            max = Math.max(max, temp);
        }
        return max;
    }
    /**
     * 求最大子矩形的主方法
     * @param map 待求解的矩形
     * @return 返回最大矩形中1的数量
     */
    public static int maxRexSize(int[][] map) {
        if (map==null || map.length==0 || map[0].length==0) {
            return 0;
        }
        int max = 0;
        /*
         * 本题的思路是首先以第0行为底计算出最大矩形，然后再将第2行叠加上去计算最大矩形，依次第3行……
         *      * 需要注意的是在叠加的过程中能够如果为1则直接加，如果为0则叠加后的结果直接为0
         */
        int[] height = new int[map[0].length];//heigth是整个矩形的列，初始值都为0
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j <map[0].length ; j++) {
                height[j] = map[i][j]==0?0:height[j]+1;
            }
            System.out.println(Arrays.toString(height));
            max = Math.max(max, maxRecFromBottom(height));
        }
        return max;
    }
    public static void main(String[] args) {
        int[][] map = {
                {1,0,1,1},
                {1,1,1,1},
                {1,1,1,0}
                    };
        /*测试maxRecFromBottom是否正常
        int[] heigth = {3,2,3,0};
        System.out.println(maxRecFromBottom(heigth));
        */
        System.out.println(maxRexSize(map));
    }
}
```