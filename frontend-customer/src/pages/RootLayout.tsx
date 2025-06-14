import { Outlet } from "react-router"
import Header from "./header/HeaderContainer"

function RootLayout() {
  return (
    <div className="pt-24 pb-10 min-h-dvh">
        <Header/>
        <Outlet/>
    </div>
  )
}

export default RootLayout