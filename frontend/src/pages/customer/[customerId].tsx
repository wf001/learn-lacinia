import { ContentTitle } from '@/components/ContentTitle'
import { EmptyTable } from '@/components/EmptyTable'
import { TextLink } from '@/components/TextLink'
import { ErrorAlert } from '@/components/alert/Error'
import { Navigation } from '@/components/Navigation'
import { useAddressByCustomer, useCustomerId } from '@/hooks/customer'
import { Table, TableBody, TableCell, TableRow } from '@mui/material'

export default function Cusotmer() {
  const customerId = useCustomerId()
  const { data, isLoading, error } = useAddressByCustomer(
    customerId,
    customerId !== -1,
  )

  if (isLoading) return null

  const info = data?.customer?.address

  return (
    <div>
      <Navigation />
      <ContentTitle title={'Customer info'} />

      {error && <ErrorAlert />}

      {!data || !data.customer ? (
        <EmptyTable />
      ) : (
        <>
          <Table>
            <TableBody>
              <TableRow>
                <TableCell>Name</TableCell>
                <TableCell>
                  {data?.customer.first_name} {data?.customer.last_name}
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Address</TableCell>
                <TableCell>
                  {info?.address} {info?.address2} {info?.city?.city},{' '}
                  {info?.city?.country?.country}
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell>postal code</TableCell>
                <TableCell>{info?.postal_code}</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>phone</TableCell>
                <TableCell>{info?.phone}</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>rental</TableCell>
                <TableCell>
                  <TextLink
                    href={`/customer/${customerId}/rental`}
                    text={'see detail'}
                  />
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </>
      )}
    </div>
  )
}
