import { CheckCircle, XCircle, FileClock, type LucideProps } from "lucide-react"
import type { ForwardRefExoticComponent, RefAttributes } from "react"

interface StatusOption{
    value: string
    label: string
    icon: ForwardRefExoticComponent<Omit<LucideProps, "ref"> & RefAttributes<SVGSVGElement>>
    color: string
}

export const STATUS_OPTIONS : StatusOption[] = [
  {
    value: "active",
    label: "Active",
    icon: CheckCircle,
    color: 'text-green-500'
  },
  {
    value: "inactive",
    label: "Inactive",
    icon: XCircle,
    color: 'text-red-500'
  },
  {
    value: "draft",
    label: "Draft",
    icon: FileClock,
    color: 'text-gray-500'
  },
] as const
