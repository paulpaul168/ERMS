import { useState } from "react";
import "./App.css";
import MessageCard from "./MessageCard";
import { useInterval } from "./util";
import Message from "./Message";
import { MapFC } from "./Map";

function App() {
  const [messages, setMessages] = useState([] as Message[]);
  const [messageIds, setMessageIds] = useState(new Set() as Set<string>);
  const [update, setUpdate] = useState(0)
  const [eventLocations, setEventLocations] = useState<number[]>([])

  useInterval(async () => {
    const response = await fetch("https://erms.stefhol.eu/api/v1/events?checked=false", {});
    const messages: Message[] = await response.json();
    if (messages.some((m) => !messageIds.has(m.id)) || messages.length !== messageIds.size) {
      setMessageIds(new Set(messages.map((m) => m.id)));
      setUpdate(prev => prev + 1)
      setEventLocations(messages.map(m => m.location));
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
      <article className="mx-auto ">
        <MapFC eventLocations={eventLocations} update={update} messages={messages} />
      </article>
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
