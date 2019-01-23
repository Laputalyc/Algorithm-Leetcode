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

![单调栈](https://raw.githubusercontent.com/Laputalyc/Algorithm-Leetcode/master/image/%E5%8D%95%E8%B0%83%E6%A0%88/001.PNG)  

如图所示：将数组`[4, 3, 2, 5]`等效成如图所示的直方图，用小正方形的数量代表各索引出的数字大小。先如要求在这个直方图中构成的最大矩形的面积（每个小正方形的面积为1）该怎么求呢？
这个其实就很好想了，我们首先以索引0所在的列往两边扩，能扩出的最大矩形的面积计算出即来，对于它来说它往右扩的索引为1，往左扩的索引为-1，所以最大矩形面积为1*4=4,同理可得其它列的情况，而这就可以用单调栈来处理，但这个单调栈是找两边的较小值。所以只需要变通一下限制单调栈是自底向上由小到大即可。

#### 3.2.3 [代码实现](https://github.com/Laputalyc/Algorithm-Leetcode/blob/master/src/%E5%8D%95%E8%B0%83%E6%A0%88/MaxSubRec.java)

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

### 3.3 山问题

#### 3.3.1 题目描述

一个数组看成一个环形山，规定符合以下情况的两座山能相互看到：

* 相邻两座山能相互看到；
* 两座山之间有顺时针和逆时针两条路径互通，只要其中至少一条路径中间没有比两座山高的山（即数值更大），则两座山可以相互看到。

给定输入，返回能看到的山的对数。

**示例：**

```
输入：[1, 2, 4, 5, 3]
输出：7
解释：(1,2),(1,3),(2,4),(4,5),(3,5),(2,3),(3,4)这7对山之间可以相互看到
```

#### 3.3.1 思路

先来个上帝视角：当输入**数组中的值都不相同**时，输入数组的长度为`1`，则山的对数为`0`;输入数组的长度为`2`，则山的对数为`1`;输入数组的长度为`i(i>=3)`，则山的对数为`2*i-3`。但这是怎么来的呢？我们来分析`i>=3`的情况：一个数组中肯定存在最高山和次高山，那么在最高山和次高山之间的其它山肯定都存在`2`条路径（这个地方好好想一想，我假设找符合条件的山的对数的方法是由小找大），算上最高峰和次高峰之间的`1`条路径，所以总共有`2*(i-2)+1`，即为`2*i-3`对。

当没有相同值的情况我们已经解决了，但是当**有相同值**的时候我们该怎么处理呢？使用**单调栈**来解决，单调栈中的元素是一种结点，这种结点我们规定如下：

```
class Mountain {
	int element;//山的高度
	int nums;//出现的次数，初始值为1
}
```

我们还是采用从小山找大山的方法，首先一遍遍历找到数组中第一次出现最大值，并把该元素形成结点压入栈中。然后向后遍历，如果数组中的元素比当前栈顶的元素小，则压栈；如果相同则将nums的次数加1；如果大于栈顶的元素，则弹栈，一旦弹栈就开始结算符合条件的对数：如果nums的值为1，则所弹出元素符合条件山的对数为2；**如果nums的值为i，则符合条件的山的对数为:**`C(2,i)+i*2`,其中`C(2,i)`代表这`i`个相同的山两两之间都可以看到。遍历到最后，栈中还有剩余，然后对栈中剩余的元素进行结算。

![单调栈](https://raw.githubusercontent.com/Laputalyc/Algorithm-Leetcode/master/image/%E5%8D%95%E8%B0%83%E6%A0%88/002.PNG) 

栈中剩余元素如上图所示：对于`i>=3`的元素，它们符合条件山的对数为`C(2,i)+2*i`;对于`i`等于`2`的元素它的山的对数要看`a`是否为`1`,如果a不为`1`，位置`2`处山的对数为`C(2,b)+b*2`,如果`a`为`1`，则它的山的对数为`C(2,b)+b*1`;对于位置`1`出的元素同样也是分情况讨论，当`a`为`1`时，它形成的山的对数为`0`,不为`1`时，形成的山的对数为`C(2,a)`。

#### 3.3.2 [代码实现](https://github.com/Laputalyc/Algorithm-Leetcode/blob/master/src/%E5%8D%95%E8%B0%83%E6%A0%88/Mountain.java)

```
class MountainNode {
    int element;//对于数组中的元素
    int nums;//该元素累计的次数
    public MountainNode(int element, int nums) {
        this.element = element;
        this.nums = nums;
    }
}
public class Mountain {
    /**
     * 获得能相互通信山的对数
     * @param arr 山的构造（看成环形数组）
     * @return 能相互通信的山的对数
     */
    public static int getMountains(int[] arr) {
        //处理基本情况
        if (arr==null || arr.length==0 || arr.length==1) {
            return 0;
        }
        if (arr.length == 2) {
            return 1;
        }
        int res = 0;
        //声明一个栈来作为单调栈处理问题
        Stack<MountainNode> stack = new Stack<>();
        //获得数组中最高峰的值以及第一次出现的索引
        MountainNode peak = getPeak(arr);
        //解析peak
        int max = peak.element;//最高峰
        int index = peak.nums;//最高峰第一次出现的索引
        //最高峰进栈
        stack.push(new MountainNode(max, 1));//初始数量为1
        //开始循环遍历，知道再次到index时停止循环
        //int start = index+1;
        /*
         * 遍历数组，更新单调栈
         */
        for (int start = index +1; start < index+arr.length; start++) {
            //start如果越界了，则返回0索引处重新循环
            start = start>=arr.length? start-arr.length: start;
            res+=process(arr, stack, start);
        }
        //至此循环完毕，然后操作栈中的剩余元素
        res += getRemainStack(stack);
        return res;
    }
    /**
     * 操作栈中剩余的元素
     * @param stack 单调栈
     * @return 操作剩余的单调栈所符合条件的山的对数
     */
    public static int getRemainStack(Stack<MountainNode> stack) {
        int size = stack.size();
        int res = 0;
        if (size > 2) {
            while (stack.size() != 2) {
                MountainNode temp = stack.pop();
                int k = temp.nums;//弹出元素在栈中累计的次数
                //C(2,i) + i*2
                res = res + k*(k-1)/2 + k*2;
            }
            //处理2位置处的元素
            res += getRemainStackTwo(stack);
        } else if (size ==2) {
            res = getRemainStackTwo(stack);
        } else {
            int k = stack.pop().nums;
            res = k==1? 0 :k*(k-1)/2;
        }
        return res;
    }
    /**
     * 处理栈中1、2位置处的元素
     * @param stack 单调栈
     * @return 符合条件山的对数
     */
    public static  int getRemainStackTwo(Stack<MountainNode> stack) {
        int res = 0;
        //首先将2位置处的结点弹出来
        MountainNode temp = stack.pop();
        int k = temp.nums;
        int j = stack.peek().nums;//最大值出现的次数
        if (j == 1) {
            res = res + k*(k-1)/2 + k;
        } else {
            res = res + k*(k-1)/2 + k*2 + j*(j-1)/2;
        }
        return res;
    }
    /**
     * 获得数组中最高峰的值以及第一次出现的索引
     * @param arr 环形山脉
     * @return 最高峰的值以及第一次出现的索引
     */
    public static MountainNode getPeak(int[] arr) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                index = i;
            }
        }
        return new MountainNode(max, index);//这里复用了一下MountainNode这个类，将nums当做最大值的索引了
    }
    /**
     * 把start索引处的元素压入栈中
     * @param arr 环形山
     * @param stack 单调栈
     * @param start 待入栈的元素索引
     * @return 因为插入一个元素而产生符合条件的山的对数
     */
    public static int process(int[] arr, Stack<MountainNode> stack, int start) {
        int res = 0;
        if (arr[start] < stack.peek().element) {
            stack.push(new MountainNode(arr[start], 1));
        } else if (arr[start] == stack.peek().element) {
            stack.peek().nums++;
        } else {
            //将小于的弹出，再压进去，弹出的时候开始结算
            while (stack.peek().element < arr[start]) {
                //比当前元素小的弹出
                MountainNode temp = stack.pop();
                int k = temp.nums;//弹出元素在栈中累计的次数
                if (k == 1) {//只有两边两个比它高的峰
                    res += 2;
                } else {
                    //C(2,i) + i*2
                    res = res + k * (k - 1) / 2 + k * 2;
                }
            }
            //这时候再把当前索引处的元素压栈
           return process(arr, stack, start) + res ;
        }
        return res;
    }
    public static void main(String[] args) {
        int[] arr = {1,2,4,5,3};//7
        int[] arr1 = {5,3,3,4};//6
        int[] arr2 = {5,3,4,3,4,4,4,5};//19
        System.out.println(getMountains(arr2));
    }
}
```