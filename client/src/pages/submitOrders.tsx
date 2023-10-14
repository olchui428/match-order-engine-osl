import Image from 'next/image'
import { Inter } from 'next/font/google'
import React, { useState, useEffect } from 'react'
import ReactDom from 'react-dom'
import * as Service from '@/Service'
import _ from 'lodash'
import { Sidebar } from '../components/Sidebar'

export default function submitOrders() {
  const [input, setInput] = useState('')
  const [logs, setLogs] = useState<String[]>([])

  const submit = async () => {
    const resp: String[] = await Service.call(
      'post',
      '/submitOrders',
      input.replace(/\n/g, ',').replace(/\,+/g, ','),
    )
    setLogs(logs.concat(resp))
  }

  const instruction = [
    `Input orders below`,
    `Buy/Sell order: <B||S> <price> <quantity> <instrument>`,
    `Cancel order: C <cancel-order-id>`,
  ]

  return (
    <div className="flex gap-12 h-screen w-full justify-center items-center px-12">
      <div className="h-4/5 w-1/4 flex flex-col gap-5 justify-start">
        <div>
          <h2 className="text-lg text-center mb-1">{instruction[0]}</h2>
          <p className="text-xs">{instruction[1]}</p>
          <p className="text-xs">{instruction[2]}</p>
        </div>
        <textarea
          className=" p-5 h-full w-full whitespace-pre-line resize-none rounded-xl"
          id="message"
          name="message"
          value={input}
          onChange={(e) => {
            setInput(e.target.value)
          }}
        ></textarea>

        <button
          type="button"
          className="text-[#4b8ddb] hover:text-white border border-[#4b8ddb] hover:bg-[#4b8ddb] focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2 mb-2 dark:border-blue-500 dark:text-blue-500 dark:hover:text-white dark:hover:bg-blue-500 dark:focus:ring-blue-800"
          onClick={() => submit()}
        >
          SUBMIT
        </button>
      </div>
      <div className="h-4/5 w-3/4  flex flex-col gap-5">
        <h2 className="text-lg text-center">Output</h2>
        <div className="overflow-auto rounded-xl p-5 border border-gray-600">
          {_.map(logs, (log) => (
            <div className="border-1">{log}</div>
          ))}
        </div>
      </div>
    </div>
  )
}
