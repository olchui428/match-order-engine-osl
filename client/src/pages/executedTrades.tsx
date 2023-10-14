import Image from 'next/image'
import { Inter } from 'next/font/google'
import React, { useState, useEffect } from 'react'
import ReactDom from 'react-dom'
import * as Service from '@/Service'
import _ from 'lodash'
import { Sidebar } from '../components/Sidebar'

type Trade = {
  timestamp: string
  price: number
  quantity: number
  buyOrderId: number
  sellOrderId: number
}

export default function executedTrades() {
  const [trades, setTrades] = useState<Trade[]>()
  const [input, setInput] = useState('')
  const searchTrades = async () => {
    const resp: Trade[] = await Service.call('get', '/getExecutedTrades', {
      instrument: input,
    })
    setTrades(resp)
  }
  const dummy = [
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 1,
      sellOrderId: 1,
      timestamp: '2023-10-14T18:51:31.124557',
    },
    {
      quantity: 15,
      buyOrderId: 3,
      price: 13.5,
      txId: 0,
      sellOrderId: 0,
      timestamp: '2023-10-14T18:50:59.623670',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
    {
      quantity: 15,
      buyOrderId: 4,
      price: 13.5,
      txId: 2,
      sellOrderId: 2,
      timestamp: '2023-10-14T18:51:31.125439',
    },
  ]
  return (
    <div className="flex flex-col gap-12 h-screen w-full justify-center items-center p-12">
      <div className="w-full">
        <div>
          <label
            htmlFor="default-search"
            className="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white"
          >
            Search
          </label>
          <div className="relative">
            <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <svg
                className="w-4 h-4 text-gray-500 dark:text-gray-400"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 20 20"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
                />
              </svg>
            </div>
            <input
              type="search"
              id="default-search"
              className="block w-full p-4 pl-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Search instrument..."
              onChange={(e) => {
                setInput(e.target.value)
              }}
              required
            ></input>
            <button
              type="button"
              onClick={() => searchTrades()}
              className="text-white absolute right-2.5 bottom-2.5 bg-[#4b8ddb] hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              Search
            </button>
          </div>
        </div>
      </div>

      <div className="h-3/4 w-full overflow-auto rounded-xl border border-gray-600 ">
        <table className="table-fixed w-full text-center ">
          <thead className="sticky top-0 bg-gray-50">
            <tr>
              <th className="p-4 font-medium">Timestamp</th>
              <th className="p-4 font-medium">Price</th>
              <th className="p-4 font-medium">Quantity</th>
              <th className="p-4 font-medium">Buy Order ID</th>
              <th className="p-4 font-medium">Sell Order ID</th>
            </tr>
          </thead>
          <tbody className=" overflow-auto mt-5">
            {_.map(trades, (trade) => (
              <tr>
                <td>{trade.timestamp}</td>
                <td>{trade.price}</td>
                <td>{trade.quantity}</td>
                <td>{trade.buyOrderId}</td>
                <td>{trade.sellOrderId}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}
