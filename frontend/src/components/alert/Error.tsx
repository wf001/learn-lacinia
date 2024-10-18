import { Alert, AlertTitle } from '@mui/material'

export const ErrorAlert = ({
  body,
  title,
}: {
  body?: string
  title?: string
}) => (
  <Alert severity="error">
    <AlertTitle>{title ? title : 'Error'}</AlertTitle>
    {body ? body : 'API request failed, please retry after a while.'}
  </Alert>
)
