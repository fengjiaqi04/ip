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
## Features (User Guide)

You can type commands into the GUI input box and press **Send**.

---
### View all commands
>help

---

### Add a todo

**Format**
>todo `<description>`

**Example**
>todo read book


---

### Add a deadline
Harden supports **date only** or **date + time**.

**Format**
>deadline `<description>` /by `<yyyy-MM-dd>`
>
>deadline `<description>` /by `<yyyy-MM-dd HHmm>`

**Examples**
>deadline return book /by 2026-03-01
> 
>deadline return book /by 2026-03-01 1800


If you provide **date only**, Harden uses **23:59** as the default time.

---

### Add an event
**Format**

>event `<description>` /from `<yyyy-MM-dd HHmm>` /to `<yyyy-MM-dd HHmm>`

**Example**

>event project meeting /from 2026-03-01 1400 /to 2026-03-01 1600

---

### List tasks
Shows all tasks currently stored.


>list

---

### Mark a task as done
**Format**

>mark `<task number>`

**Example**

>mark 2


---

### Unmark a task (mark as not done)
**Format**

>unmark `<task number>`

**Example**

>unmark 2


---

### Delete a task
Removes the task at the given index.

**Format**

>delete `<task number>`

**Example**

>delete 3

---

### Find tasks by keyword
Finds tasks whose description contains the keyword.

**Format**
>find `<keyword>`


**Example**
>find book

---

### Exit the app


>bye