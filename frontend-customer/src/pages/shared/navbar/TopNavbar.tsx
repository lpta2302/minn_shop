import { useEffect, useState } from "react"
import { HeaderLogo } from "../logo"
import MainTopNavigation from "./MainTopNavigation"
import TopToolbar from "./TopToolbar"
function TopNavbar() {
    const [isSearching, setIsSearching] = useState<boolean>(false)
    const [searchParams, setSearchParams] = useState({})
    
  return (
    <div
        className={`
            bg-white
            w-full
            flex
            items-center
            justify-between
            h-16
        `}
    >
        <HeaderLogo/>
        {
            isSearching ||
            <MainTopNavigation/>
        }
        <TopToolbar setSearchParams={setSearchParams} isSearching={isSearching} setIsSearching={setIsSearching}/>
    </div>
  )
}

export default TopNavbar