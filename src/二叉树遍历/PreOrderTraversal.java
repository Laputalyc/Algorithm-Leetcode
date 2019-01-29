package niuke;

import java.util.Stack;

class BiNode {
    int val;
    BiNode left;
    BiNode right;
    public BiNode(int val) {
        this.val = val;
    }
}

/**
 * 二叉树的先序遍历
 */
public class PreOrderTraversal {
    /**
     * 先序遍历二叉树的非递归版本
     * @param head 指定二叉树的头结点
     */
    public static void preOrderTraversal1(BiNode head) {
        if (head == null) {
            throw new RuntimeException("指定的二叉树无效！");
        }
        //准备一个栈
        Stack<BiNode> stack = new Stack<>();
        //将头结点压入栈中
        stack.push(head);
        //循环的终止条件是栈不为空
        while (!stack.isEmpty()) {
            //1.进入循环即弹出栈顶元素并输出
            head = stack.pop();
            System.out.print(head.val + " ");
            //2.右子树和左子树分别进栈
            if (head.right != null) {
                stack.push(head.right);
            }
            if (head.left != null) {
                stack.push(head.left);
            }
        }
    }

    /**
     * 二叉树先序遍历的递归版本
     * @param head 指定二叉树的头结点
     */
    public static void preOrderTraversal2(BiNode head) {
        if (head == null) {
            return;
        }
        //1.第一次遍历即输出
        System.out.print(head.val + " ");
        //2.按照先序遍历的方式接下来遍历左子树
        preOrderTraversal2(head.left);
        //3.按照先序遍历的方式接下来遍历右子树
        preOrderTraversal2(head.right);
    }

    public static void main(String[] args) {
        BiNode head = new BiNode(1);
        head.left = new BiNode(2);
        head.right = new BiNode(3);
        head.left.right = new BiNode(4);
        head.right.left = new BiNode(5);
        preOrderTraversal1(head);
        System.out.println("-------------------");
        preOrderTraversal2(head);
    }
}
