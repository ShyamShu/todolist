import React, { useState, useEffect } from "react";
import API from "../api";
import { useNavigate } from "react-router-dom";
import "./TodoList.css";

const TodoList = () => {
  const [tasks, setTasks] = useState([]);
  const [newTask, setNewTask] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await API.get("/task");
      console.log("Fetched tasks:", response.data);
      setTasks(response.data);

    } catch (error) {
      if (error.response) {
        if (error.response.status === 401) {
          alert("Session expired. Please log in again.");
          localStorage.removeItem("token");
          navigate("/login");
        } else {
          alert("Error fetching tasks: " + error.response.data.message);
        }
      } else {
        alert("Network error: " + error.message);
      }
    }
  };

  const addTask = async () => {
    if (newTask.trim()) {
      try {
        const response = await API.post("/task", {
          description: newTask,
          completed: false,
        });
        //setTasks([...tasks, response.data]);
        setNewTask("");
        fetchTasks();
      } catch (error) {
        alert("Error adding task: " + error.message);
      }
    }
  };

  const deleteTask = async (id) => {
    try {
      await API.delete(`/task/${id}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });
      setTasks(tasks.filter((task) => task.id !== id));
    } catch (error) {
      alert("Error deleting task: " + error.message);
    }
  };

  const toggleTaskCompletion = async (task) => {
    try {
      const updatedTask = { ...task, completed: !task.completed };
      const response = await API.put(`/task/${task.id}`, updatedTask);
      setTasks(tasks.map((t) => (t.id === task.id ? response.data : t)));
    } catch (error) {
      alert("Error updating task: " + error.message);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="todo-container">
      <header className="todo-header">
        <h1>To-Do List</h1>
        <button className="logout-btn" onClick={handleLogout}>
          Logout
        </button>
      </header>
      <div className="task-input">
        <input
          type="text"
          placeholder="Add a new task..."
          value={newTask}
          onChange={(e) => setNewTask(e.target.value)}
        />
        <button className="add-btn" onClick={addTask} disabled={!newTask.trim()}>
          Add Task
        </button>
      </div>
      <div className="task-list">
        {tasks.length === 0 ? (
          <p className="no-tasks">No tasks available. Add a task!</p>
        ) : (
          tasks.map((task) => (
            <div
              key={task.id}
              className={`task-item ${task.completed ? "completed" : ""}`}
            >
              <span
                className="task-text"
                onClick={() => toggleTaskCompletion(task)}
              >
                {task.description}
              </span>
              <button
                className="delete-btn"
                onClick={() => deleteTask(task.id)}
              >
                Delete
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default TodoList;
