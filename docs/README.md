# Harden

Harden is a task manager chatbot.
It supports todos, deadlines, and events, and saves tasks automatically.

---
## What it looks like
You can find it [here](Ui.png)
## Quick start

### Requirements
- Java 17 (recommended)

### Run
```
java -jar harden.jar
```
---
## Features (User Guide)

You can type commands into the GUI input box and press **Send**.

---

### View all commands

You can check all the existing commands
```text
help
```

---

### Add a todo

**Format**
```text
todo <description>
```

**Example**
```text
todo read book
```

---

### Add a deadline
Harden supports **date only** or **date + time** in deadline.

**Format**
```text
deadline <description> /by <yyyy-MM-dd>

deadline <description> /by <yyyy-MM-dd HHmm>
```
**Examples**
```text
deadline return book /by 2026-03-01
 
deadline return book /by 2026-03-01 1800
```

If you provide **date only**, Harden uses **23:59** as the default time.

---

### Add an event
**Format**
```text
event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>
```
**Example**
```text
event project meeting /from 2026-03-01 1400 /to 2026-03-01 1600
```
---

### List tasks
Shows all tasks currently stored.

```text
list
```
---

### Mark a task as done
**Format**
```text
mark <task number>
```
**Example**
```text
mark 2
```

---

### Unmark a task (mark as not done)
**Format**
```text
unmark <task number>
```
**Example**
```text
unmark 2
```

---

### Delete a task
Removes the task at the given index.

**Format**
```text
delete <task number>
```
**Example**
```text
delete 3
```
---

### Find tasks by keyword
Finds tasks whose description contains the keyword.

**Format**
```text
find <keyword>
```

**Example**
```text
find book
```
---

### Exit the app
```text
bye
```