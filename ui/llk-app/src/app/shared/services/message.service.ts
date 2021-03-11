/*
 * Copyright (C) 2020 LifeLab Kids.
 * All Rights Reserved
 */
let msgResource = {
  llkPortal: {
    appUrls: {
      web: {
        local: 'localhost',
        dev: '35.203.150.148',
        prod: 'lifelabkids.org',
      },
      api: {
        local: 'http://localhost:8999/',
        dev: 'http://35.203.150.148/',
        prod: 'http://35.203.150.148/',
      },
      version: {
        local: 'api/v1/',
        dev: 'api/v1/'
      },
      patchUrls: {
        searchTherapist: 'therapist/api/v1/search',
        updateTherapist: 'therapist/api/v1/update/',
        deleteTherapist: 'therapist/api/v1/delete/',
        detailTherapist: 'therapist/api/v1/detail/',
        addTherapist: 'therapist/api/v1/save',
        levels: 'therapist/api/v1/levels',
        getAllTherapist: 'therapist/api/v1/all',
        communicationmodes: 'therapist/api/v1/communicationmodes',
        therapyTypes: 'therapist/api/v1/therapytypes',
        remainders: 'therapist/api/v1/lov/calremainder',
        repeats: 'therapist/api/v1/lov/calrepeats',
        genders: 'therapist/api/v1/lov/gender',
        therapists: 'therapist/api/v1/all',
        cancelTherapistEvent: 'therapist/api/v1/cancelschedule',
        updateTherapistEvent: 'therapist/api/v1/updateschedule',
        therapyavailabilities: 'therapist/api/v1/therapyavailabilities',
        searchClient: 'client/api/v1/search',
        updateClient: 'client/api/v1/update/',
        deleteClient: 'client/api/v1/delete/',
        detailClient: 'client/api/v1/detail/',
        addClient: 'client/api/v1/save',
        getAllClient: 'client/api/v1/all',
        clients: 'client/api/v1/all',
        eventBooking: 'client/api/v1/saveschedule'
      }
    },
    msalConfig: {
      msalBaseUrl: 'https://login.microsoftonline.com/',
      clientId_prod: '637cbebc-dae0-4172-bbf7-ad8b81205941',
      clientId_dev: '637cbebc-dae0-4172-bbf7-ad8b81205941',
      tenantId: '1e590b1f-5fff-4959-ab08-a301115e8026'
    },
    roles: {
      admin: 'admin',
      therapist: 'therapist',
      client: 'client'
    },
    errorMessages: {
      signinError: 'Error in signin',
      signinSilentError: 'Error in silent signin'
    }
  }
};
export {
  msgResource
};
