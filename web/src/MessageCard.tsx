function formatHourAndMinute(datetime: Date): string {
  const hour = datetime.getHours().toString().padStart(2, "0");
  const minute = datetime.getMinutes().toString().padStart(2, "0");
  return `${hour}:${minute}`;
}
const MessageCard: React.FC<{
  title: string;
  time: Date;
  location: string;
}> = ({ title, time, location }) => {
  async function changeChecked(e: Event) {
    e.preventDefault();
    const res = await fetch("http://erms.stefhol.eu/api/v1/events", {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: "flo",
    });
  }
  return (
    <div className="bg-white rounded-xl drop-shadow-md p-3 mb-5">
      <div className="flex justify-between">
        <span className="font-bold text-xl">{title}</span>
        <span>{formatHourAndMinute(time)}</span>
      </div>
      <div>Location: {location}</div>
      <div className="w-full flex justify-center">
        <button
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
};

export default MessageCard;
