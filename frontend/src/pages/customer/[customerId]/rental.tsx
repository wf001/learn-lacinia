import { ContentTitle } from '@/components/ContentTitle'
import { EmptyTable } from '@/components/EmptyTable'
import { TextLink } from '@/components/TextLink'
import { ErrorAlert } from '@/components/alert/Error'
import { Navigation } from '@/components/Navigation'
import { useCustomerId, useRentalInfoByCustomer } from '@/hooks/customer'
import { toYYYYMMDD } from '@/lib/dayjs'
import { Table, TableBody, TableCell, TableRow } from '@mui/material'

export default function Rental() {
  const customerId = useCustomerId()
  const { data, isLoading, error } = useRentalInfoByCustomer(
    customerId,
    customerId !== -1,
  )

  if (isLoading) return null

  const info = data?.customer?.payment

  return (
    <div className={''}>
      <Navigation />
      <ContentTitle title={'Customer rental info'} />

      {error && <ErrorAlert />}

      {!info ? (
        <EmptyTable />
      ) : (
        <>
          {info.map((r, key) => (
            <div key={key}>
              <Table className={'mt-10'}>
                <TableBody>
                  <TableRow>
                    <TableCell>rental duration</TableCell>
                    <TableCell>
                      {toYYYYMMDD(r?.rental?.rental_date)} -{' '}
                      {toYYYYMMDD(r?.rental?.return_date)}
                    </TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>amount</TableCell>
                    <TableCell>{r?.amount}</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>payment</TableCell>
                    <TableCell>{toYYYYMMDD(r?.payment_date)}</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>film</TableCell>
                    <TableCell>
                      <TextLink
                        href={`/film/${r?.rental?.inventory?.film_id}`}
                        text={`see detail`}
                      />
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
            </div>
          ))}
        </>
      )}
    </div>
  )
}
