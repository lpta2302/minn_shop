"use client"

import {
    type ColumnDef,
    type ColumnFiltersState,
    flexRender,
    getCoreRowModel,
    getFilteredRowModel,
    getPaginationRowModel,
    getSortedRowModel,
    type Row,
    type SortingState,
    useReactTable,
    type VisibilityState
} from "@tanstack/react-table"

import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import { useMemo, useState, type Dispatch, type SetStateAction } from "react"
import { DataTableViewOptions } from "./DataViewOption"
import { DataTablePagination } from "./DataTablePagination"
import { Button } from "@/components/ui/button"
import { Pencil, Trash2 } from "lucide-react"

interface DataTableProps<TData, TValue> {
    columns: ColumnDef<TData, TValue>[]
    data: TData[]
    setUpdatingRow: Dispatch<SetStateAction<string | undefined>> | undefined
    setDeletingRow: Dispatch<SetStateAction<string | undefined>> | undefined
}

export function DataTable<TData, TValue>({
    columns: originalColumns,
    data,
    setUpdatingRow,
    setDeletingRow
}: DataTableProps<TData, TValue>) {
    const [sorting, setSorting] = useState<SortingState>([])
    const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>(
        []
    )
    const [columnVisibility, setColumnVisibility] =
        useState<VisibilityState>({})
    const [rowSelection, setRowSelection] = useState({})

    const columns = useMemo(() => {
        const handleDeleteClick = (row: Row<TData>) => {
            if(!setDeletingRow)
                return
            
            if (confirm(`All related information can be deleted. Are you sure to delete?`)) {
                setDeletingRow(row.id)
            }
        }
        const handleUpdateClick = (row: Row<TData>) => {
            if(!setUpdatingRow)
                return
            setUpdatingRow(row.id)
        }
        const actionColumn =
            setDeletingRow || setUpdatingRow ?
                {
                    id: "actions",
                    cell: ({ row }: { row: Row<TData> }) => {
                        return (
                            <div>
                                {
                                    setUpdatingRow &&
                                    <Button
                                        variant={"ghost"}
                                        onClick={() => handleUpdateClick(row)}
                                    >
                                        <Pencil />
                                    </Button>}
                                {
                                    setDeletingRow &&
                                    <Button
                                        variant={"ghost"}
                                        className="text-red-700"
                                        onClick={() => handleDeleteClick(row)}
                                    >
                                        <Trash2 />
                                    </Button>
                                }
                            </div>
                        )
                    },
                } : undefined
        return actionColumn ? [...originalColumns, actionColumn] : originalColumns
    }, [originalColumns, setDeletingRow, setUpdatingRow])

    const table = useReactTable({
        data,
        columns,
        getCoreRowModel: getCoreRowModel(),
        getPaginationRowModel: getPaginationRowModel(),
        onSortingChange: setSorting,
        getSortedRowModel: getSortedRowModel(),
        onColumnFiltersChange: setColumnFilters,
        getFilteredRowModel: getFilteredRowModel(),
        onColumnVisibilityChange: setColumnVisibility,
        onRowSelectionChange: setRowSelection,
        state: {
            sorting,
            columnFilters,
            columnVisibility,
            rowSelection,
        }
    })

    return (
        <div>
            <div className="flex items-center py-4">
                <DataTableViewOptions table={table} />
            </div>
            <div className="rounded-md border">
                <Table>
                    <TableHeader>
                        {table.getHeaderGroups().map((headerGroup) => (
                            <TableRow key={headerGroup.id}>
                                {headerGroup.headers.map((header) => {
                                    return (
                                        <TableHead key={header.id}>
                                            {header.isPlaceholder
                                                ? null
                                                : flexRender(
                                                    header.column.columnDef.header,
                                                    header.getContext()
                                                )}
                                        </TableHead>
                                    )
                                })}
                            </TableRow>
                        ))}
                    </TableHeader>
                    <TableBody>
                        {table.getRowModel().rows?.length ? (
                            table.getRowModel().rows.map((row) => (
                                <TableRow
                                    key={row.id}
                                    data-state={row.getIsSelected() && "selected"}
                                >
                                    {row.getVisibleCells().map((cell) => (
                                        <TableCell key={cell.id}>
                                            {flexRender(cell.column.columnDef.cell, cell.getContext())}
                                        </TableCell>
                                    ))}
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={columns.length} className="h-24 text-center">
                                    No results.
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </div>
            <DataTablePagination table={table} />
        </div>
    )
}
