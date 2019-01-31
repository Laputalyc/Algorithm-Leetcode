package niuke;

/**
 * Morris遍历
 */
public class MorrisTraversal {
    public static void main(String[] args) {
        BiNode head = new BiNode(4);
        head.left = new BiNode(2);
        head.right = new BiNode(6);
        head.left.left = new BiNode(1);
        head.left.right = new BiNode(3);
        head.right.left = new BiNode(5);
        head.right.right = new BiNode(7);
        morrisIn(head);
        System.out.println();
        morrisPre(head);
    }

    public static void morrisPos(BiNode head) {
        if (head == null) {
            return;
        }
        BiNode cur = head;
        BiNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        printEdge(head);
        System.out.println();
    }

    public static void printEdge(BiNode head) {
        BiNode tail = reverseEdge(head);
        BiNode cur = tail;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    public static BiNode reverseEdge(BiNode from) {
        BiNode pre = null;
        BiNode next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }
    /**
     * Morris遍历思想实现中序遍历
     * @param head 指定二叉树的头结点
     */
    public static void morrisIn(BiNode head) {
        if (head == null) {
            throw new RuntimeException("指定二叉树无效！");
        }
        BiNode cur = head;//当前结点
        BiNode mostRight = null;//树的最右结点
        //循环的条件是cur不为空
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {//cur结点有左子树
                //首先找到左子树的最右结点,注意是两个条件
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                //如果mostRight的right为null，则让right指向cur并且指针左移
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                } else {//说明是第二次来到cur结点，则让mostRight的指针复原指向null并且cur右移
                    mostRight.right = null;
                    //中序遍历第二次访问该节点时打印
                    System.out.print(cur.val + " ");
                    cur = cur.right;
                }
            } else {
                //没有左子树那么就是该结点只会被访问一次，所以直接打印然后指针右移
                System.out.print(cur.val + " ");
                cur = cur.right;
            }
        }
    }

    /**
     * Morris思想实现先序遍历
     * @param head 指定二叉树的头结点
     */
    public static void morrisPre(BiNode head) {
        if (head == null) {
            throw new RuntimeException("指定的二叉树无效！");
        }
        BiNode cur = head;//当前结点
        BiNode mostRight = null;//树的最右结点
        //循环的条件是cur不为空
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {//cur结点有左子树
                //首先找到左子树的最右结点,注意是两个条件
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                //如果mostRight的right为null，则让right指向cur并且指针左移
                if (mostRight.right == null) {//第一次来到该结点
                    mostRight.right = cur;
                    //先序遍历第一次访问时打印该结点信息
                    System.out.print(cur.val + " ");
                    cur = cur.left;
                } else {//说明是第二次来到cur结点，则让mostRight的指针复原指向null并且cur右移
                    mostRight.right = null;
                    cur = cur.right;
                }
            } else {
                //没有左子树那么就是该结点只会被访问一次，所以直接打印然后指针右移
                System.out.print(cur.val + " ");
                cur = cur.right;
            }
        }
    }
}
