import { ContentTitle } from '@/components/ContentTitle'
import { EmptyTable } from '@/components/EmptyTable'
import { Page } from '@/components/Pagination'
import { TextLink } from '@/components/TextLink'
import { ErrorAlert } from '@/components/alert/Error'
import { useCustomers } from '@/hooks'
import { Table, TableBody, TableCell, TableHead, TableRow } from '@mui/material'
import React, { useState } from 'react'

export default function Home() {
  const [currentPage, setCurrentPage] = useState(1)
  const { data, isLoading, error } = useCustomers(currentPage, 10)
  if (isLoading) return null
  const isEpmty = !data?.paginatedCustomers?.customers

  return (
    <div>
      <ContentTitle title={'Customers list'} />

      {error && <ErrorAlert />}

      {isEpmty ? (
        <EmptyTable />
      ) : (
        <>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>
                  <p>name</p>
                </TableCell>
                <TableCell>
                  <p>email</p>
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(data?.paginatedCustomers?.customers ?? []).map((c, key) => (
                <TableRow key={key}>
                  <TableCell>
                    <TextLink
                      href={`/customer/${c?.customer_id}`}
                      text={`${c?.first_name} ${c?.last_name}`}
                    />
                  </TableCell>
                  <TableCell>
                    <p>{c?.email}</p>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          <Page currentPage={currentPage} setPage={setCurrentPage} />
        </>
      )}
    </div>
  )
}
