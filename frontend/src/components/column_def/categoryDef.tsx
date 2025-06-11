import type { Category } from "@/types";
import type { ColumnDef } from "@tanstack/react-table";;
import { DataTableColumnHeader } from "../shared/data-table/DataTableColumnHeader";
import StatusBadge from "../shared/badge/StatusBadge";

export const categoryColumnDef: ColumnDef<Category>[] = [
    {
        accessorKey: "code",
        header: ({ column }) => {
            return (
                <DataTableColumnHeader column={column} title='Code'/>
            )
        },

    },
    {
        accessorKey: "name",
        header: ({ column }) => {
            return (
               <DataTableColumnHeader column={column} title='Name'/>
            )
        },
    },
    {
        accessorKey: "status",
        header: ({ column }) => {
            return (
                <DataTableColumnHeader column={column} title='Status'/>
            )
        },
        cell: ({ cell }) => (
            <StatusBadge
                value={
                    cell.renderValue() ?
                    cell.renderValue() as string :
                    undefined
                }
            />
        ),
    },
    {
        accessorKey: "parentId",
        header: ({ column }) => {
            return (
                <DataTableColumnHeader column={column} title='Parent Id'/>
            )
        },
    }
]