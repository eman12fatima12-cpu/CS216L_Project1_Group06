# Student Record System

## Description
Student Record System is a Java console-based application built for managing student academic records efficiently. The system implements dynamic memory management through a Singly Linked List and tracks deleted records using a Stack to provide an instant undo capability. Users can perform full CRUD operations, search for students, and sort records by GPA.

## Data Structures Used
* **Singly Linked List:** Used to store and dynamically manage student records (ID, Name, GPA).
* **Stack:** Used to preserve deleted student records, enabling a Last-In, First-Out (LIFO) "Undo Last Delete" functionality.

## Features
1. **Add Student Record:** Insert new student details into the system.
2. **Display All Records:** Traverse and display all active student records.
3. **Search Student:** Find student details by ID using Linear Search.
4. **Delete Student Record:** Remove a record from the linked list and push it onto the Undo Stack.
5. **Sort Records by GPA:** Sort all student records in descending order of GPA using Bubble Sort.
6. **Undo Last Delete:** Pop the most recently deleted student from the Undo Stack and restore it to the linked list.

## Time Complexity Analysis

| Operation | Data Structure / Algorithm | Time Complexity |
| :--- | :--- | :--- |
| **Insert Record** | Singly Linked List | $O(1)$ at Head / $O(n)$ at Tail |
| **Delete Record** | Singly Linked List | $O(n)$ |
| **Search Record** | Linear Search | $O(n)$ |
| **Sort Records (by GPA)** | Bubble Sort | $O(n^2)$ |
| **Push to Undo Stack** | Stack Push | $O(1)$ |
| **Undo Last Delete** | Stack Pop | $O(1)$ |

## Repository Link
https://eman12fatima12-cpu.github.io/CS216L_Project1_Group06/
