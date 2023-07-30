import React from "react";
import {
  Popover,
  PopoverTrigger,
  PopoverContent,
  PopoverDescription,
  PopoverHeading,
  PopoverClose
} from "popover";

function Uncontrolled() {
  return (
    <div className="App">
      <h1>Floating UI — Popover</h1>
      <Popover>
        <PopoverTrigger>My trigger</PopoverTrigger>
        <PopoverContent className="Popover">
          <PopoverHeading>My popover heading</PopoverHeading>
          <PopoverDescription>My popover description</PopoverDescription>
          <PopoverClose>Close</PopoverClose>
        </PopoverContent>
      </Popover>
    </div>
  );
}
