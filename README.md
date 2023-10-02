# tuicodechallenge

This application returns Github repositories by the owner's userName, excluding any forked repositories.

The response includes following information:

- Repository name,
- Owner's login,
- List of branches, with each branch providing its name and SHA of the last commit.

If the provided username does not exist, the application will respond with a 404 error.

Additionally, if the API consumer only accepts XML data, the application will respond with a 406 error.

## API Endpoint

You can access the API by using the following endpoint: ```/api/users/{userName}/repositories```