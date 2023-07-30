import { useEffect, useMemo, useRef } from "react"
import { Tooltip, TooltipContent, TooltipTrigger } from "./Tooltip";

type MapProps = {
  eventLocations: number[]
  update: number
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
            <Cell key={idx + str} value={str} nmbr={idx} />
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
}
const Cell: React.FC<CellProps> = ({ value, nmbr }) => {
  return (
    <>
      <Tooltip>
        <TooltipTrigger>
          <div className={`cell ${value === 'x' ? "active" : ""}`} />
        </TooltipTrigger>
        <TooltipContent className="Tooltip">
          <div className="bg-white" >
{nmbr}
          </div>
        </TooltipContent>
      </Tooltip>

    </>
  )


}


