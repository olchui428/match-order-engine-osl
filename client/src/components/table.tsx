import { FunctionComponent } from 'react'
import JsonData from '../sample.json'
import _ from 'lodash'

type PriceSize = {
  size: number
  price: number
}

export type BidAsk = {
  bid: PriceSize[]
  ask: PriceSize[]
}

export interface BidAskTableProps {
  isBid: boolean
  data?: PriceSize[]
}

export const Table: FunctionComponent<BidAskTableProps> = ({ isBid, data }) => {
  const parseData = _.map(data, (info) => {
    return (
      <tr className="border-b-[1px] border-gray-300">
        {isBid ? (
          <>
            <td>{info.size}</td>
            <td className="pr-10">{info.price}</td>
          </>
        ) : (
          <>
            <td className="pl-10">{info.price}</td>
            <td>{info.size}</td>
          </>
        )}
      </tr>
    )
  })

  return (
    <div>
      <table
        className={`table-fixed w-full ${isBid ? 'text-right' : 'text-left'}`}
      >
        <thead className="sticky top-0 bg-gray-50">
          {isBid ? (
            <tr>
              <th className="py-4 font-medium">Size</th>
              <th className="py-4 pr-10 font-medium">Bid</th>
            </tr>
          ) : (
            <tr>
              <th className="py-4 pl-10 font-medium">Ask</th>
              <th className="py-4 font-medium">Size</th>
            </tr>
          )}
        </thead>
        <tbody>{parseData}</tbody>
      </table>
    </div>
  )
}
