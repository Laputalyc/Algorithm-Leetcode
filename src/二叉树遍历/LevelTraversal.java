package niuke;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的层序遍历
 */
public class LevelTraversal {
    /**
     * 二叉树层序遍历的非递归版本
     * @param head
     */
    public static void levelTraversal1(BiNode head) {
        if (head == null) {
            throw new RuntimeException("指定的二叉树无效！");
        }
        //申请一个队列作为辅助
        Queue<BiNode> queue = new LinkedList<>();
        queue.add(head);
        //循环的条件是队列不为空
        while (!queue.isEmpty()) {
            //拿走队头元素并打印
            head = queue.poll();
            System.out.print(head.val + " ");
            //左、右结点分别入队
            if (head.left != null) {
                queue.add(head.left);
            }
            if (head.right != null) {
                queue.add(head.right);
            }
        }
    }

    /**
     * 二叉树层次遍历的递归版本
     * @param head 指定二叉树的头结点
     */
    public static void levelTraversal2(BiNode head) {
        if (head == null) {
            throw new RuntimeException("指定的二叉树无效！");
        }
        //获取二叉树的高度
        int depth = getDepth(head);
        for (int i = 1; i <= depth ; i++) {
            levelTraversal2(head, i);
        }
    }

    /**
     * 打印第k层的结点信息
     * @param head 指定二叉树的头结点
     * @param level 该树的高度
     */
    public static void levelTraversal2(BiNode head, int level) {
        if (head == null) {//这个判断的作用主要是为了防止左右子树高度不一致出现的空指针异常，并不是basic case。
            return;
        }
        if (level == 1) {
            System.out.print(head.val + " ");
            return;
        }
        //对左子树和右子树分别进行层次遍历
        levelTraversal2(head.left, level-1);
        levelTraversal2(head.right, level-1);
    }

    /**
     * 获得一颗树的高度
     * @param head 指定二叉树的头结点
     * @return 树的高度（从1开始计数）
     */
    public static int getDepth(BiNode head) {
        if (head == null) {
            return 0;
        }
        return Math.max(getDepth(head.left), getDepth(head.right)) + 1;
    }
    public static void main(String[] args) {
        BiNode head = new BiNode(1);
        head.left = new BiNode(2);
        head.right = new BiNode(3);
        head.left.right = new BiNode(4);
        head.right.left = new BiNode(5);
        levelTraversal1(head);
        System.out.println("-------------------");
        levelTraversal2(head);
    }
}
