import { cn } from "@/lib/utils"
import { Loader2Icon } from "lucide-react"

interface LoadingProps {
    className?: string,
    size?: string
}

function Loading({className, size}:LoadingProps) {
  return (
    <div 
        className={cn("flex items-center justify-center", className)}    
    >
        <Loader2Icon 
            className={`
                animate-spin
                ${size}    
            `}
        />
    </div>
  )
}

export default Loading