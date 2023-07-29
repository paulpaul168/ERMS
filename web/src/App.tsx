//import { useState } from "react";
import "./App.css";

function MessageCard() {
  return (
    <div className="bg-white rounded-xl drop-shadow-md p-3">
      <div className="flex justify-between">
        <span className="font-bg">Trage benötigt</span>
        <span>22:16</span>
      </div>
      <div>Location: G7</div>
    </div>
  );
}

function App() {
  return (
    <>
      <div className="w-full p-4">
        <div className="max-w-lg mx-auto">
          <h1 className="text-3xl font-bold">Messages</h1>
        </div>
      </div>
      <main className="w-full max-w-lg">{MessageCard()}</main>
    </>
  );
}

export default App;
