import { useState } from "react";
import "./App.css";
import MessageCard from "./MessageCard";
import { useInterval } from "./util";

type Message = {
  message: string;
  location: string;
  date: string;
  checked: boolean;
  id: string;
};

function App() {
  const [messages, setMessages] = useState([] as Message[]);

  useInterval(async () => {
    const response = await fetch("https://erms.stefhol.eu/api/v1/events", {
      //mode: "no-cors",
    });
    console.log(response);
    const messages: Message[] = await response.json();
    //console.log(messages);

    // TODO: maybe less rebuild with hashset and figuring out if stuff is new
    setMessages(messages);
  }, 1000);

  return (
    <>
      <div className="w-full p-4">
        <div className="max-w-lg mx-auto">
          <h1 className="text-3xl font-bold">Messages</h1>
        </div>
      </div>
      <main className="w-full max-w-lg mx-auto">
        {messages.length > 0 ? (
          messages.map((m) => (
            <MessageCard
              title={m.message}
              location={m.location}
              time={new Date(m.date)}
            />
          ))
        ) : (
          <div className="font-italic w-full flex justify-center pt-24">
            No open messages 🎉{" "}
          </div>
        )}
      </main>
    </>
  );
}

export default App;
