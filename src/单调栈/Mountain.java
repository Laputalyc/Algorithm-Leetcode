package niuke;

import java.util.Stack;

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
