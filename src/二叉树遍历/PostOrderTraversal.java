package niuke;

import java.util.Stack;

/**
 * 二叉树的后序遍历
 */
public class PostOrderTraversal {
    /**
     * 二叉树后序遍历的非递归版本
     * @param head 指定二叉树的头结点
     */
    public static void postOrderTraversal1(BiNode head) {
        if (head == null) {
            throw new RuntimeException("指定的二叉树无效！");
        }
        //申请一个栈
        Stack<BiNode> stack = new Stack<>();
        //再申请一个栈用于最后逆序输出
        Stack<BiNode> post = new Stack<>();
        //当前结点进栈
        stack.push(head);
        //循环的终止条件是栈不为空
        while (!stack.isEmpty()) {
            //弹出栈顶元素，原本该打印的地方不打印了，因为最后有一个逆序关系所以放入栈中
            head = stack.pop();
            post.push(head);
            //左子树先进栈，右子树再进栈，这样输出时就是中、右、左
            if (head.left != null) {
                stack.push(head.left);
            }
            if (head.right != null) {
                stack.push(head.right);
            }
        }
        //把post栈中的元素输出即为后序遍历的顺序
        while (!post.isEmpty()) {
            System.out.print(post.pop().val + " ");
        }
    }

    /**
     * 二叉树后序遍历的递归版本
     * @param head 指定二叉树的头结点
     */
    public static void postOrderTraversal2(BiNode head) {
        if (head == null) {
            return;
        }
        //把左、右子树都遍历完第三次访问该节点时再输出
        postOrderTraversal2(head.left);
        postOrderTraversal2(head.right);
        System.out.print(head.val + " ");
    }
    public static void main(String[] args) {
        BiNode head = new BiNode(1);
        head.left = new BiNode(2);
        head.right = new BiNode(3);
        head.left.right = new BiNode(4);
        head.right.left = new BiNode(5);
        postOrderTraversal1(head);
        System.out.println("-------------------");
        postOrderTraversal2(head);
    }
}
