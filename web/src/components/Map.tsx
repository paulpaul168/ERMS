import { useMemo } from "react"
import Message from "../Message";
import { Popover, PopoverContent, PopoverTrigger } from "../ui-util/Popover";
import MessageCard from "../ui-util/MessageCard";

type MapProps = {
  eventLocations: number[]
  update: number
  messages: Message[]
}
export const MapFC: React.FC<MapProps> = (props) => {
  const divs = useMemo(() => {
    const arr = Array.from(' '.repeat(900 - 18))
    for (let i = 0; i < props.eventLocations.length; i++) {
      const event = props.eventLocations[i]
      arr[event] = 'x'
    }
    return arr
  }, [props.update])


  return (<>
    <div
      style={{ position: "relative" }}
    >



      <div className="mx-auto" style={{ width: 1280 / 1.5, height: 720 / 1.5 }}>
        <div className="cells" style={{ width: 'inherit', height: 'inherit' }}>
          {divs.map((str, idx) => (
            <Cell key={idx + str} value={str} nmbr={idx} messages={props.messages} />
          ))}
        </div>

        <canvas id="map-canvas" style={{ width: 1280 / 1.5, height: 720 / 1.5 }} />
      </div>
    </div>
  </>
  )
}
type CellProps = {
  value: string
  nmbr: number
  messages: Message[]
}
const Cell: React.FC<CellProps> = ({ value, nmbr: location, messages }) => {
  return (
    <>
      <Popover>
        <PopoverTrigger>
          <div className={`cell ${value === 'x' ? "active" : ""}`} />
        </PopoverTrigger>
        <PopoverContent className="Tooltip">
          <div className="flex flex-col" >
            {value === "x" ?
              messages.filter(m => m.location === location).map(m => (
                <MessageCard message={m} />
              ))
              :
              <span className="bg-white rounded-xl drop-shadow-md p-3 mb-5">
                Location: {location}
              </span>
            }
          </div>
        </PopoverContent>
      </Popover>

    </>
  )
}



