# AI-Assisted Development Log (A-AiAssisted)

## Tools used
- ChatGPT (OpenAI)

## How AI assisted this project
I used ChatGPT to support development and verification tasks while implementing and polishing the Harden chatbot increments. Specifically, AI helped with:
- Suggesting safe assertion placements (Java `assert`) for key assumptions in the codebase.
- Refactoring guidance to improve code quality without changing program behavior (e.g., reducing duplication, clearer structure, safer IO handling patterns).
- Implementing a small extension (`help` command) and integrating it into parsing logic.
- Providing Git workflow steps (parallel branches, PR merge commit workflow, tagging, and syncing branches).

## What I did with AI suggestions
- I reviewed the suggestions and adapted them to match my project’s existing method signatures and UI methods (e.g., using `Ui.showMessage(...)`).
- I verified changes by compiling/running the app and ensuring existing commands still behaved correctly.

## Notes
- AI suggestions were used as guidance; final edits were integrated to match the project’s style, APIs, and module requirements.
