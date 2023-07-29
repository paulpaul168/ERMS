import { useState } from "react";
import "./App.css";
import MessageCard from "./MessageCard";
import { useInterval } from "./util";
import Message from "./Message";

function App() {
  const [messages, setMessages] = useState([] as Message[]);
  const [messageIds, setMessageIds] = useState(new Set() as Set<string>);

  useInterval(async () => {
    const response = await fetch("https://erms.stefhol.eu/api/v1/events", {});
    const messages: Message[] = await response.json();

    if (messages.some((m) => !messageIds.has(m.id))) {
      setMessageIds(new Set(messages.map((m) => m.id)));
      setMessages(messages);
    }
  }, 1000);

  return (
    <>
      <div className="w-full p-4">
        <div className="max-w-lg mx-auto">
          <h1 className="text-3xl font-bold">Messages</h1>
        </div>
      </div>
      <main className="w-full max-w-lg mx-auto p-4">
        {messages.length > 0 ? (
          messages.map((m) => <MessageCard key={m.id} message={m} />)
        ) : (
          <div className="italic w-full flex justify-center pt-24">
            No open messages 🎉{" "}
          </div>
        )}
      </main>
    </>
  );
}

export default App;
