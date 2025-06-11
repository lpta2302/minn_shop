import { Input } from '@/components/ui/input'
import { type Table } from '@tanstack/react-table'

interface TDataTableSearchBarProps{
    table: Table<unknown>
}

function DataTableSearchBar({table}: TDataTableSearchBarProps) {
    return (<div className="flex items-center py-4">
        <Input
            placeholder="Filter emails..."
            value={(table.getColumn("email")?.getFilterValue() as string) ?? ""}
            onChange={(event) =>
                table.getColumn("email")?.setFilterValue(event.target.value)
            }
            className="max-w-sm"
        />
    </div>)
}

export default DataTableSearchBar