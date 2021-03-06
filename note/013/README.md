# [罗马数字转整数](https://leetcode-cn.com/problems/roman-to-integer/)

## 题目描述

罗马数字包含以下七种字符: `I`，` V`，` X`，` L`，`C`，`D` 和` M`。
```
字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
```
例如， 罗马数字 2 写做 `II` ，即为两个并列的 1。12 写做 `XII` ，即为` X + II `。 27 写做  `XXVII`, 即为 `XX + V + II` 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 `IIII`，而是 `IV`。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为` IX`。这个特殊的规则只适用于以下六种情况：

`I `可以放在 `V `(5) 和 `X` (10) 的左边，来表示 4 和 9。
`X` 可以放在 `L` (50) 和 `C` (100) 的左边，来表示 40 和 90。 
`C `可以放在 `D` (500) 和`M`(1000) 的左边，来表示 400 和 900。
给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。

**示例 1:**

```
输入: "III"
输出: 3
```

**示例 2:**

```
输入: "IV"
输出: 4
```

**示例 3:**

```
输入: "IX"
输出: 9
```

**示例 4:**

```
输入: "LVIII"
输出: 58
解释: L = 50, V= 5, III = 3.
```

**示例 5:**

```
输入: "MCMXCIV"
输出: 1994
解释: M = 1000, CM = 900, XC = 90, IV = 4.
```

## 思路
用switch进行判断，在有分歧的分支进行判断即可。

* 字符串本身是没有索引的，要想获得字符串中第i位置处的字符可不能用`str[i]`。用String中的方法`str.charAt(i)`来获取位置i处的字符。
* 当判断当前元素与后一个元素能否构成特殊罗马数字时要先判断是否越界。例如，`(i+1)<s.length() &&s.charAt(i+1) == 'V' `。

## 代码实现

```
class Solution {
    public int romanToInt(String s) {
        int result = 0;
        for(int i=0; i<s.length(); i++) {
            switch(s.charAt(i)) {
                case 'I':
                    if((i+1)<s.length() &&s.charAt(i+1) == 'V') {
                        result = result + 4;
                        i++;
                    } else if((i+1)<s.length() &&s.charAt(i+1) == 'X') {
                        result = result + 9;
                        i++;
                    } else {
                        result = result + 1;
                    }
                    break;
                case 'V':
                    result = result + 5;
                    break;
                case 'X':
                    if((i+1)<s.length() &&s.charAt(i+1) == 'L') {
                        result = result + 40;
                        i++;
                    } else if((i+1)<s.length() &&s.charAt(i+1) == 'C') {
                        result = result + 90;
                        i++;
                    } else {
                        result = result + 10;
                    }
                    break;
                case 'L':
                    result += 50;
                    break;
                case 'C':
                    if((i+1)<s.length() &&s.charAt(i+1) == 'D') {
                        result = result + 400;
                        i++;
                    } else if((i+1)<s.length() &&s.charAt(i+1) == 'M') {
                        result = result + 900;
                        i++;
                    } else {
                        result = result + 100;
                    }
                    break;
                case 'D':
                    result += 500;
                    break;
                case 'M':
                    result += 1000;
                    break;
            }
        }
        return result;
    }
}
```