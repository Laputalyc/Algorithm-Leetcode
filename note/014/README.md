# [最长公共前缀](https://leetcode-cn.com/problems/longest-common-prefix/)

## 题目描述

编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 `""`。

**示例 1:**

```
输入: ["flower","flow","flight"]
输出: "fl"
```

**示例 2:**

```
输入: ["dog","racecar","car"]
输出: ""
解释: 输入不存在公共前缀。
```

**说明:**

所有输入只包含小写字母 `a-z `。

## 思路

首先用一个额外数组（len）记录输入字符串数组中各字符串的长度，其次求出数组len中的最小值（目的是为了在接下来比较的时候确定比较的最大范围），以及索引值（目的是为了在比较完成都没有不同之后，说明最长公共前缀即为输入字符串数组中长度最短的字符串），依次比较各个字符串中不同索引的字符，直至不同，返回从0索引开始到不同字符之前的子串，这个子串即为所求最长公共前缀。

* 区分获取数组的长度和字符串的长度：获取数组的长度是`arr.length`，获取字符串的长度是`str.length()`。
* 获取字串的方法`substring(int beginIndex, int endIndex)`，这个方法`string`的首字母是小写，它所获得的字串是包含`beginIndex`索引出的字符，不包括`endIndex`索引出的字符。

## 代码实现

```
class Solution {
    	public String longestCommonPrefix(String[] strs) {
                if(strs.length < 1 || strs == null) {
                    return "";
                }
                if(strs.length == 1) {
                    return strs[0];
                }
                int[] len = new int[strs.length];
                for(int i=0; i<strs.length; i++) {
                    len[i] = strs[i].length();
                }
                int min = len[0];
                int index = 0;
                for(int i=1; i<len.length; i++) {
                    if(len[i] < min) {
                        min = len[i];
                        index = i;
                    }
                }
                int j;
                for(j=0; j<min; j++) {
                    char compare = strs[0].charAt(j);
                    for(int k=1; k<strs.length; k++) {
                        if(compare != strs[k].charAt(j)) {
                            return strs[0].substring(0, j); 
                        }
                    }
                }
                return strs[index];
        }
}
```

## 补充

关于这题，Leetcode上给出了详细的[题解](https://leetcode-cn.com/problems/longest-common-prefix/solution/)，大家可以参考。