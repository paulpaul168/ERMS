import { useState } from "react";
import "./App.css";
import { useInterval } from "./util";
import Message from "./Message";
import { MapFC } from "./components/Map";
import MessageCard from "./ui-util/MessageCard";
import Slide from '@mui/material/Slide';
import React from "react";
import Logo from './assets/logo.svg';
import Profile from './assets/profile.svg';

function arraysHaveSameElements(array1: string[], array2: string[]) {
  // Check if both arrays have the same length
  if (array1.length !== array2.length) {
    return false;
  }

  // Sort the arrays to ignore the order
  const sortedArray1 = array1.slice().sort();
  const sortedArray2 = array2.slice().sort();

  // Compare the sorted arrays element by element
  for (let i = 0; i < sortedArray1.length; i++) {
    if (sortedArray1[i] !== sortedArray2[i]) {
      return false;
    }
  }

  return true;
}

function App() {
  const [messages, setMessages] = useState([] as Message[]);
  //const [messageIds, setMessageIds] = useState(new Set() as Set<string>);
  const [update, setUpdate] = useState(0);
  const [eventLocations, setEventLocations] = useState<number[]>([]);

  useInterval(async () => {
    const response = await fetch(
      "https://erms.stefhol.eu/api/v1/events?checked=false",
      {}
    );
    const newMessages: Message[] = await response.json();
    // This is ugly AF, but it works now and I cannot get it to work otherwise
    if (
      !arraysHaveSameElements(
        newMessages.map((m) => m.id),
        messages.map((m) => m.id)
      ) ||
      true
    ) {
      setUpdate((prev) => prev + 1);
      setEventLocations(messages.map((m) => m.location));
      setMessages(newMessages);
    }
  }, 1000);

  async function dispatchCallback(id: string) {
    const _res = await fetch("https://erms.stefhol.eu/api/v1/events", {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: id,
        checked: true,
      }),
    });
    // const newMessages = messages.filter((m) => m.id != id);
    // setMessages(newMessages);
    // setUpdate((prev) => prev + 1);
    // setEventLocations(newMessages.map((m) => m.location));
  }

  return (
    <>
    {/* <nav className="bg-white border-gray-200 dark:bg-gray-300 flex">
      <img src={Logo} className="max-h-16"></img>
      <div className="grow"></div>
      <img src={Profile} className="max-h-16 p-2"></img>
    </nav> */}
      <div className="grid grid-cols-3 gap-8 m-4 mx-28">
        <h1 className="text-3xl font-bold col-span-3">Emergencies</h1>
        <article className="col-span-2">
          <MapFC eventLocations={eventLocations} update={update} messages={messages} />
        </article>
        <main className="col-span-1">

          {messages.length > 0 ? (
            messages.map((m) => <CardWrapper key={m.id} message={m} dispatchCallback={dispatchCallback} />)
          ) : (
            <div className="italic w-full flex justify-center pt-24">
              No open messages 🎉{" "}
            </div>
          )}
        </main>
      </div>
    </>
  );
}
const CardWrapper: React.FC<{
  message: Message;
  dispatchCallback: (id: string) => void;
}> = React.forwardRef((props, ref) => {
  const [inc, setInc] = useState(true)
  function dispatchCallback(id: string) {
    setInc(false)
    props.dispatchCallback(id)
  }
  return (
    <Slide in={inc} mountOnEnter direction="left">

      <MessageCard
        //@ts-ignore
        ref={ref}
        message={props.message}
        dispatchCallback={dispatchCallback}
      />
    </Slide>
  )
})

export default App;
