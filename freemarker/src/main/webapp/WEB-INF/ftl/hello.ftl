${hello} <br>
<#-- ${key} : 取出map中key为hello的值
这就是freemarker中的插值，
插值有如下两种类型:
    1,通用插值${expr};
    2,数字格式化插值:#{expr}或#{expr;format}
-->
<#--  -->
<#-- 对于通用插值,又可以分为以下4种情况:  -->
<#-- 1,插值结果为字符串值:直接输出表达式结果  -->

<#-- 2,插值结果为数字值:根据默认格式(由#setting指令设置)将表达式结果转换成文本输出.
可以使用内建的字符串函数格式化单个插值,如下面的例子:  -->
<#setting number_format="currency"/> <#-- http://freemarker.foofun.cn/ref_directive_setting.html-->
<#-- 四种预定义的数字格式，分别是computer（跟内建函数c功能一样，用法不一样）、
currency（货币格式）、number（数字格式）、percent（百分比形式） -->
<#assign score=42/> <#-- assign指令：创建或替换一个顶层变量,或者创建或替换多个变量等 -->
${score} <br>
${score?string} <br>
${score?string.number} <br>
${score?string.currency} <br>
${score?string.percent} <br>
<#-- 这些格式的明确含义是本地化（国家）指定的，
受Java平台安装环境所控制，而不是FreeMarker，
所以不建议用这些函数，而且受默认数字格式的影响，用法不灵活。
可采用另外一种方式来灵活控制：number?string(expr) 详细参考：https://blog.csdn.net/sayoko06/article/details/80319002
 -->



