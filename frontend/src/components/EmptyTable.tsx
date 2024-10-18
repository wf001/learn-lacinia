import { Table, TableCell, TableHead, TableRow } from '@mui/material'

export const EmptyTable = () => (
  <Table>
    <TableHead>
      <TableRow>
        <TableCell>
          <p>No data found</p>
        </TableCell>
      </TableRow>
    </TableHead>
  </Table>
)
