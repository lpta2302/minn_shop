import { cn } from "@/lib/utils"
import SubTopBarLinks from "./SubTopBarLink"

function SubTopNavbar() {
  return (
    <div
        className={
            cn(
                'w-full',
                'flex',
                'items-center',
                'justify-between',
                'h-8',
                'bg-white-smoke'
            )
        }
    >
        <div></div>
        <div></div>
        <SubTopBarLinks/>
    </div>
  )
}

export default SubTopNavbar