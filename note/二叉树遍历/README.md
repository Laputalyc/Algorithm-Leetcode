# 二叉树的遍历

## 1.二叉树的基本遍历算法

所谓二叉树的基本遍历算法是指我们通常所说的先序遍历、中序遍历、后续遍历、层序遍历。这样说主要是为了区分最后我们将介绍的Morris遍历。对于前三种遍历会介绍递归版本和非递归版本，对于层序遍历只会介绍非递归版本。

## 1.1 先序遍历（PreOrderTraversal）

### 1.1.1 非递归版本

先序遍历的顺序是中、左、右。它的思想是利用一个栈，首先头结点进栈，然后分别判断右孩子和左孩子是否为空（**由于中、左、右的顺序所以进栈的时候是先进右后进左**），不为空则进栈，循环的终止条件是栈为空，每次进入循环即弹出栈顶元素。

```
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
```

### 1.1.2 递归版本

其实不管是先序遍历、中序遍历、后续遍历，**二叉树中的每个结点总是会被访问到3次**,而最终决定该算法是先序、中序还是后序的关键在于我们在何时打印该结点信息，**如果在第一次访问时打印即为先序遍历，在第二次访问时打印即为中序遍历，在第三次访问时打印即为后续遍历**（关于3次访问这个概念大家一定要自己动手画一画,在这里不做赘述）。

```
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
```

## 1.2 中序遍历（InOrderTraversal）

### 1.2.1 非递归版本

中序遍历的遍历顺序是左、中、右。仍然是利用一个额外的栈。循环的终止条件是栈为空并且当前结点为空，当前结点不为空时，该结点进栈，并且指针向左移动(`head = head.left`)，当前结点为空时弹出栈顶元素，指针向右移动(`head = stack.pop(); head = head.right`)。与先序遍历一样，弹栈的时机即为打印的结点信息的时机。

```
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
```

### 1.2.2 递归版本

```
    public static void inOrderTraversal2(BiNode head) {
        if (head == null) {
            return;
        }
        inOrderTraversal2(head.left);
        //先遍历完左子树，在第二次访问该结点的时候输出
        System.out.print(head.val + " ");
        inOrderTraversal2(head.right);
    }
```

## 1.3 后序遍历（PostOrderTraversal）

### 1.3.1 非递归版本

后序遍历的顺序是左、右、中，我们注意到先序遍历的顺序是中、左、右，先序遍历的逆序输出为右、左、中。这个顺序和我们所要的后续遍历的顺序只有左右的区别，而我们知道左、右在先序遍历的实现中只是一个进栈先后的问题。所以后序遍历的非递归版本是先序遍历的一个变形。

```
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
```

### 1.3.2 递归版本

```
    public static void postOrderTraversal2(BiNode head) {
        if (head == null) {
            return;
        }
        //把左、右子树都遍历完第三次访问该节点时再输出
        postOrderTraversal2(head.left);
        postOrderTraversal2(head.right);
        System.out.print(head.val + " ");
    }
```

## 1.4 层序遍历

### 1.4.1 非递归版本

经过上述的思维训练，我们很容易想到层序遍历的非递归版本。它所借助的额外数据结构是一个队列，循环的终止条件是队列不为空，一旦进入循环即拿出队头元素并打印，同时队头结点的左孩子、右孩子分别入队。

```
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
```

### 1.4.2 递归版本

层次遍历的递归版本相对前三种来说思维难度高一些。假设现在有一颗树它的高度为k(从1开始计数)，那么我们要对这棵二叉树进行层次遍历，就要分别层次遍历处每一层的结点。当`k==1`时直接打印，而当`k != 1`时，层次遍历它的左子树和右子树。（这个说起来比较绕，仔细体会代码）。

```
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
```

## 1.5 基本遍历算法总结

以上这四种算法的时间复杂度都为`O(n)`，空间复杂度最坏情况下为`O(n)`,最好情况为`O(log n)`，其中`n`为二叉树结点的个数。这四种算法是属于最基本的算法，大家应该做到拿到就能从头到尾的码出来。当达到这个熟练度之后，请大家思考一下这个额外空间的最好、最坏情况是怎么来的？这个额外空间是用来存储什么的？还有什么叫做线索二叉树？当然这些问题在下文会有解答，但我强烈建议大家先独立思考一下，这会对接下来要介绍的Morris遍历有极大的促进作用。
## 1.先导问题

首先要介绍一种一种时间复杂度为O(N)，额外空间复杂度O(1)的二叉树的遍历方式，N为二叉树的结点个数。

之前我们介绍的二叉树遍历方式的额外空间复杂度为O(h)，其中h为树的高度，这是由于二叉树结点的结构来决定的，因为我们要遍历一棵树，过程中免不了要由一个结点返回它的父节点，而结点又不具备指向父节点的指针，所以这时候需要用额外的空间来存储父节点信息（说的更具体一点就是，我们找到了一个结点的左子树，现在该去找这个结点的右子树了，只有先从该左子树返回到父节点，再由父节点去找右子树）。

**遍历过程：**设来到的当前节点记为`cur`

* 如果`cur`无左孩子，`cur`向右移动(`cur = cur.right`)
* 如果`cur`有左孩子，找到`cur`左子树上最右的结点，记为`mostRight`
	* 如果`mostRight`的`right`指针指向`null`，让其指向`cur`，并且`cur`向左移动(`cur = cur.left`)
	* 如果`mostRight`的`right`指针指向`cur`，让其指向`null`，并且`cur`向右移动(`cur = cur.right`)