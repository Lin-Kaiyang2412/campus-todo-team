# CampusTodo

CampusTodo 是《软件工程》课程中用于练习 Git 与 GitHub 团队协同开发的 Java 项目。项目采用 Java 17、Maven 和 JUnit 5，可直接使用 IntelliJ IDEA 打开。

当前版本：支持新增任务和列出任务。

> 重要：上面这一行是合并冲突实验的固定锚点。只有实验任务明确要求时才修改，且不要提前合并其他同学的文字。

## 1. 初始能力

- `Task`：包含 `id`、`title` 和 `completed` 三个字段。
- `TaskService.addTask(String title)`：新增任务。
- `TaskService.listAll()`：按加入顺序返回任务快照。
- 基线测试：覆盖正常新增任务与空白标题校验。

## 2. 环境要求

- JDK 17
- IntelliJ IDEA 2023.3 或更高版本
- Maven 3.9 或 IntelliJ IDEA 内置 Maven
- Git 2.x

## 3. 打开与验证

1. 解压后，在 IntelliJ IDEA 中选择 **File → Open**，打开本目录中的 `pom.xml`。
2. 在 Project SDK 中选择 JDK 17，等待 Maven 依赖加载完成。
3. 打开 Maven 工具窗口，执行 `Lifecycle → test`；也可在终端执行：

```bash
mvn test
```

初始版本应有 2 个测试通过。

## 4. 团队迭代任务

| Issue | 负责人 | 目标 | 建议分支 |
| --- | --- | --- | --- |
| #1 Priority filter | 开发者 A | 增加 HIGH/MEDIUM/LOW，并支持按优先级筛选 | `feature/1-priority-filter` |
| #2 Complete task | 开发者 B | 按编号完成任务，并处理异常与重复完成 | `feature/2-complete-task` |
| #3 CI and guide | 质量负责人 Q | 增加 Maven CI、PR 模板并完善协作说明 | `feature/3-ci-guide` |

详细验收标准以教师发放的《实验任务书》为准。建议合并顺序为 `#3 → #1 → #2`，以便在 #2 中完成预定的 README 冲突练习。

## 5. Git 起步

本压缩包不包含 `.git` 目录。仓库管理员首次解压后执行：

```bash
git init
git add .
git commit -m "chore: initialize CampusTodo baseline"
git branch -M main
git remote add origin <REPOSITORY_URL>
git push -u origin main
git tag -a v0.1.0 -m "CampusTodo starter baseline"
git push origin v0.1.0
```

## 6. 协作约束

- 一个功能分支只解决一个 Issue，禁止直接向 `main` 提交功能代码。
- 提交信息采用 `<type>: <说明>`，例如 `test: specify task completion rules`。
- Pull Request 必须关联 Issue，并提供测试证据和自检结果。
- 作者不能批准自己的 Pull Request；评审意见处理完毕且 CI 通过后再合并。
- 不提交 `.idea/`、`target/`、访问令牌、账号密码或个人隐私数据。
- 禁止使用 `git push --force` 修改共享的 `main` 分支。

## 7. 使用说明（构建 / 运行 / 测试）

### 7.1 构建

```bash
mvn -B clean package               # 编译并打包
mvn -B clean package -DskipTests   # 只编译，不执行测试
```

构建产物位于 `target/`：`target/classes/`（编译后的字节码）、`target/campus-todo-0.1.0.jar`（打包结果）。

### 7.2 运行

本项目当前是类库（还没有 `main` 方法入口），因此通过单元测试或 `jshell` 交互来验证运行效果：

```bash
mvn -B compile
jshell --class-path target/classes
```

在 `jshell` 中可以直接调用服务类：

```java
import edu.hbuas.campustodo.service.TaskService;
var service = new TaskService();
service.addTask("写实验报告");
service.listAll();
```

使用 IntelliJ IDEA 时：**File → Open** 选择本目录的 `pom.xml`，Project SDK 设为 JDK 17，然后在测试类上右键 **Run** 即可。

### 7.3 测试

```bash
mvn -B test                                        # 运行全部单元测试
mvn -B test -Dtest=TaskServiceTest                 # 只运行指定测试类
mvn -B test -Dtest=TaskServiceTest#shouldAddTask   # 只运行指定测试方法
```

测试报告（Surefire）输出到 `target/surefire-reports/`，其中 `.txt` 为文本摘要、`.xml` 供 CI 解析。

要点：`mvn -B` 使用批处理模式，输出更适合 CI 与日志粘贴；本地首次运行需要联网下载依赖。

## 8. 持续集成（CI）

CI 由 `.github/workflows/ci.yml` 定义，以下两种情况会自动执行 `mvn -B test`：

1. 向 `main` 推送提交（`push`）；
2. 创建或更新针对 `main` 的 Pull Request（`pull_request`）。

结果查看方式：仓库 **Actions** 标签页，或 Pull Request 页面底部的 **Checks** 区域。每次运行的测试报告以构件 `surefire-reports` 上传，可在运行详情页下载。

![build](https://github.com/Lin-Kaiyang2412/campus-todo-team/actions/workflows/ci.yml/badge.svg)

约定：**CI 未通过（红色 ×）时不得合并 Pull Request**；同一个 PR 内如需修错，应继续在当前功能分支上补充提交，而不是新建分支或强制推送。
