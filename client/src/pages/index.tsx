import Image from 'next/image'
import { Inter } from 'next/font/google'
import React, { useState, useEffect } from 'react'
import ReactDom from 'react-dom'
import * as Service from '@/Service'
import _ from 'lodash'

export default function Home() {
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

  return (
    <main className="flex flex-col min-h-screen justify-center h-full items-center p-24">
      <h1 className="p-6 text-3xl font-extrabold text-gray-900 dark:text-white md:text-5xl lg:text-6xl text-transparent bg-clip-text bg-gradient-to-r to-[#aa99c9] from-sky-400">
        Match Order Engine
      </h1>
      <p className="text-lg font-normal text-gray-500 lg:text-xl dark:text-gray-400">
        Rachel Chui On Lam
      </p>
    </main>
  )
}
