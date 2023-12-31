import React from "react";
import Message from "../Message";

export function formatHourAndMinute(datetime: Date): string {
  const hour = datetime.getHours().toString().padStart(2, "0");
  const minute = datetime.getMinutes().toString().padStart(2, "0");
  return `${hour}:${minute}`;
}
const MessageCard: React.FC<{
  message: Message;
  dispatchCallback: (id: string) => void;
}> = React.forwardRef(({ message, dispatchCallback }, ref) => {
  async function changeChecked(e: Event) {
    e.preventDefault();
    dispatchCallback(message.id);
  }

  return (
    //@ts-ignore
    <div ref={ref as HTMLDivElement} className="bg-white rounded-xl drop-shadow-md p-3 mb-5">
      <div className="flex justify-between">
        <span className="font-bold text-xl">{message.message}</span>
        <span>{formatHourAndMinute(new Date(message.date))}</span>
      </div>
      <div>
        <span> {message.from}</span>
      </div>
      <div>Location: {message.location}</div>
      <div className="w-full flex justify-center">
        <button
          type="button"
          onClick={async (e) => {
            await changeChecked(e as unknown as Event);
          }}
          className="px-6  py-1 rounded-lg bg-black hover:bg-neutral-500 text-white"
        >
          Dispatch
        </button>
      </div>
    </div>
  );
})

export default MessageCard;
