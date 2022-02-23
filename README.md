# 项目名称：iTile

项目经理：岳宇轩

项目组人员： 钱思航、赵艳然、帅翔宇、程传奇、葛畅、林子靖、刘思佳

项目说明：实现一款可以有记事、工作报表、日历、管理日程的APP


# 需求文档



---

#### 说明：

* 文档中用正粗体表示的是详情页，统一在模块功能说明完毕后再具体要求
* 文档供分为三大部分：登陆注册板块、主功能板块（消息、项目、工作、个人中心）、详情页板块（日程、项目、任务详情）、新建页面板块（新建日程、新建任务、新建项目、新建子任务）

---






# 登陆注册板块 

#### 登录界面

1.登录：输入用户名和正确的密码，点击登录按钮，即可登陆成功。

2.注册：点击注册按钮，前往注册界面进行注册。

3.找回密码：如忘记密码，可以点击该按钮找回密码。

#### 注册界面

1.设置自己的用户名和邮箱地址，手机号，设置密码，点击获取验证码跳转到验证页面。

2.点击返回按钮关闭

#### 验证页面

1.用户会收到一封包含验证码的邮件，点击确定若输入验证码正确即可注册成功

2.点击按钮关闭

#### 找回密码

1.输入用户名，点击获取验证码按钮可收到用于修改密码的验证码，输入正确验证码后可设置新密码。

2.点击按钮关闭



# 主功能板块 

## 1、消息模块

#### (1）最上方搜索框（支持关键字搜索）

> * 可以搜索通讯录中的~~好友昵称~~ 、项目名称、任务名称

#### (2)  今日日程助手

> * 点击后~~显示今日日程~~ ，分为进行中和已完成(今天的)
> * 每个日程可以点击进入**日程详情页**查看具体内容，在该页可以改变日程的完成状态

#### (3)  项目助手

> 点击前如果有未读消息则显示红点，点击后显示新参与的**项目详情页**，未点击过的项目右侧有***红点***（可以用其他方式实现，意思清晰表达出未读过即可）

#### (4)  任务助手

> 点击前如果有未读消息则显示红点，点击后显示新参与的**任务详情页**，未点击过的任务右侧有***红点***（可以用其他方式实现，意思清晰表达出未读过即可）



## 2、项目模块

### 上方：工作区

#### (1)  我的任务

> * 点击进去显示任务列表，有滑块特效，共三个滑块（页面）从左到右的任务分类为：未开始、进行中、已完成。
>
> * 每个页面由若干个任务卡组成，任务卡需要显示的内容有：任务名称、所属项目名称、起始时间、截止时间。
> * 点击任务卡可以查看此任务的**任务详情页**
>
> * 每个页面都要做分页器（上拉加载......）

#### (2)  项目报表

>* 对于每个项目要求显示的信息有：任务总数、未开始的任务总数、进行中的任务总数、已完成的任务总数、项目进度、项目名称
>
>* 有分页器

### 下方：项目区

> 列出所有项目的名称，有分页器



## 3、工作模块

### 日程部分：

>* 可以添加新日程（具体内容、起始时间、截止时间）
>
>* 可以查看某一天的所有日程，默认展示今天的所有日程

### 通讯录

> * 按列表形式显示所有好友
> * ~~最上方有搜索框，可以按关键字搜索好友昵称~~~
> * 可以通过邮箱||手机号||昵称完全匹配来添加好友
> * 默认有用户本人



## 4、个人中心

> * 显示基本信息：头像、用户名、手机号、邮箱。
> * ~~显示今日日程，包括：内容、完成状态（进行中、已完成），可以点击查看日程详情。另外如果日程详情内容过长要进行截断处理~~
> * ~~今日日程以列表形式逐条显示~~





# 详情页板块（每个详情页要包含的功能）

### 1.日程详情页

>* 完成状态（可修改）
>* 起始时间
>* 截止时间
>* 日程详情



### 2.项目详情页

>* 项目名称
>* ***项目描述（可修改)***
>* 项目状态（可修改）
>* 项目进度
>* ***项目成员（可增不可减）（点击可查看所有成员）***
>* ***所有任务（点击显示所有任务，~~即：“我的任务”页）~~ ，以列表形式显示， 每条任务显示一个任务卡，注意每条任务要显示出来任务的状态， 点击单条任务跳转到该任务的详情页***
>* 可以添加任务



### 3.任务详情页

>* 任务名称
>* ***负责人（可修改）***
>* 任务状态（可修改）
>
>* 所属项目
>* ***任务成员（点击可查看所有成员）（可修改）***
>* ***任务详情（可修改）***
>* 起始时间
>* 截止时间
>* 子任务页（点击查看所有子任务、可以新建子任务、点击某条子任务跳转到子任务详情页）



### 4.子任务详情页

>* 任务名称
>* ***负责人（可修改）***
>* 任务状态（可修改）
>
>* 所属项目
>* ***任务成员（点击可查看所有成员）（可修改）***
>* ***任务详情（可修改）***
>* 起始时间
>* 截止时间



# 新建板块（需要用户输入的内容）

### 1.新建项目页面

>* 项目名称 (只有名称不为空的时候才可以按创建按钮)
>* 项目描述
>* ~~项目状态~~
>* 项目成员（只能添加通讯录里面的好友）（默认有用户自己）

### 2.新建任务页面

>* 任务名称
>* 负责人（只能添加通讯录里面的好友）
>
>* 任务成员（只能添加通讯录里面的好友）
>* 任务详情
>* 起始时间
>* 截止时间

### 3.新建子任务页面

> * 任务名称
> * 负责人（只能添加通讯录里面的好友）
>
> * 任务成员（只能添加通讯录里面的好友）
> * 任务详情
> * 起始时间
> * 截止时间

### 4.新建日程页面

> * 起始时间
> * 截止时间
> * 日程详情



---

* url页数什么带数字的要防止乱输，
