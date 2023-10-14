import '@/styles/globals.css'
import type { AppProps } from 'next/app'
import { Sidebar } from '../components/Sidebar'

export default function App({ Component, pageProps }: AppProps) {
  return (
    <div className="flex ">
      <Sidebar />
      <div className="ml-[220px] w-full">
        <Component {...pageProps} />
      </div>
    </div>
  )
}
