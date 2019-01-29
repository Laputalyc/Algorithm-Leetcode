package niuke;

import java.util.Stack;

/**
 * 二叉树的中序遍历
 */
public class InOrderTraversal {
    /**
     * 二叉树中序遍历的非递归版本
     *
     * @param head
     */
    public static void inOrderTraversal1(BiNode head) {
        if (head == null) {
            throw new RuntimeException("指定的二叉树无效！");
        }
        //准备一个栈
        Stack<BiNode> stack = new Stack<>();
        //循环的条件是栈不为空或者当前结点不为空
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                //如果是当前结点不为空,那么该结点进栈，并且那么指针向左移
                stack.push(head);
                head = head.left;
            } else {
                //如果是当前结点为空，栈不为空，则弹出栈顶元素，指针右移
                head = stack.pop();
                System.out.print(head.val + " ");
                head = head.right;
            }
        }
    }

    /**
     * 二叉树中序遍历的递归版本
     * @param head
     */
    public static void inOrderTraversal2(BiNode head) {
        if (head == null) {
            return;
        }
        inOrderTraversal2(head.left);
        //先遍历完左子树，在第二次访问该结点的时候输出
        System.out.print(head.val + " ");
        inOrderTraversal2(head.right);
    }

    public static void main(String[] args) {
        BiNode head = new BiNode(1);
        head.left = new BiNode(2);
        head.right = new BiNode(3);
        head.left.right = new BiNode(4);
        head.right.left = new BiNode(5);
        inOrderTraversal1(head);
        System.out.println("-------------------");
        inOrderTraversal2(head);
    }
}
