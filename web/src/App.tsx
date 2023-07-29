//import { useState } from "react";
import "./App.css";
import MessageCard from "./MessageCard";

function App() {
  return (
    <>
      <div className="w-full p-4">
        <div className="max-w-lg mx-auto">
          <h1 className="text-3xl font-bold">Messages</h1>
        </div>
      </div>
      <main className="w-full max-w-lg mx-auto">
        <MessageCard />
        <MessageCard />
        <MessageCard />
      </main>
    </>
  );
}

export default App;
