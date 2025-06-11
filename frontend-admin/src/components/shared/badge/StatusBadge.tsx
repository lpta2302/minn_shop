import { Badge } from "@/components/ui/badge"
import { STATUS_OPTIONS } from "@/constants"
import { camelcaseGenerator } from "@/utils"

function StatusBadge({ value } : {value: string | undefined}) {
    const option = STATUS_OPTIONS.find(option=> option.value === value)
    const label = option?.label ? option.label : camelcaseGenerator(value)
    const icon = option?.icon ? <option.icon/> : undefined

    return (
        <Badge
            variant='outline'
            className={`${option && option.color}`}
        >
            {icon && icon}
            {label}
        </Badge>
    )
}

export default StatusBadge