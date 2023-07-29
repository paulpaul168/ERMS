function MessageCard() {
  return (
    <div className="bg-white rounded-xl drop-shadow-md p-3 mb-5">
      <div className="flex justify-between">
        <span className="font-bold text-xl">Trage benötigt</span>
        <span>22:16</span>
      </div>
      <div>Location: G7</div>
      <div className="w-full flex justify-center">
        <button className="px-6  py-1 rounded-lg bg-black hover:bg-neutral-500 text-white">
          Dispatch
        </button>
      </div>
    </div>
  );
}

export default MessageCard;
