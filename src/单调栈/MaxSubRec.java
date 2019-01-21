package niuke;

import java.util.Arrays;
import java.util.Stack;

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
