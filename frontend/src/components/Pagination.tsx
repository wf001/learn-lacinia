import { Pagination } from '@mui/material'
import { Dispatch, SetStateAction } from 'react'

export const Page = ({
  currentPage,
  setPage,
  count,
}: {
  currentPage: number
  setPage: Dispatch<SetStateAction<number>>
  count?: number
}) => {
  return (
    <div className={'flex justify-center'}>
      <Pagination
        className={'mt-10'}
        count={count ? count : 10}
        page={currentPage}
        onChange={(_, value) => setPage(value)}
        color="primary"
      />
    </div>
  )
}
