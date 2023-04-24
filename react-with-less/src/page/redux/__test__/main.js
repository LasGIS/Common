import { createAsyncThunk, createSelector, createSlice } from '@reduxjs/toolkit';
import * as service from 'Services/remote/international-monitoring-service';
import { hashHistory } from 'react-router';
import { FIELDS, getExcelTask, parseExcelTasks } from '../main/mainUtils';
import {
  calcLastWeek,
  initialFilterState,
  initialSortState,
  normalizeLocationQuery,
  sendNotificationCreateTaskFailed,
  sendNotificationCreateTaskSuccess,
} from '../../utils';
import { isCompletedDispatchMainForm, purificationDispatchMainForm } from '../utils';
import { setReportError, setShowSpinner } from '../../../../../app/common';

const calcInitialForm = () => {
  const query = normalizeLocationQuery(hashHistory.getCurrentLocation().query);
  return isCompletedDispatchMainForm(query) ? purificationDispatchMainForm(query) : calcLastWeek();
};

export const getDispatchMainReport = createAsyncThunk('main-getDispatchMainReport', (form, { dispatch, rejectWithValue, getState }) => {
  dispatch(setShowSpinner(true));
  return service
    .getDispatchMainReport(form)
    .then((response) => {
      const { result } = response;
      return result.reportLines || [];
    })
    .catch((error) => {
      dispatch(setReportError(error));
      return rejectWithValue('finish');
    })
    .finally(() => {
      dispatch(setShowSpinner(false));
    });
});

const mainSlice = createSlice({
  name: 'main',
  initialState: {
    form: { dateFrom: null, dateTo: null },
    isSearchPassed: false,
  },
  reducers: {
    clearSearch(state) {
      state.isSearchPassed = false;
      state.running = 'finish';
    },
    setForm(state, action) {
      state.form = action.payload;
      state.isSearchPassed = false;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(getDispatchMainReport.pending.type, (state) => {
        state.isSearchPassed = false;
        state.running = 'start';
        state.reportLines = [];
      })
      .addCase(getDispatchMainReport.fulfilled.type, (state, action) => {
        state.isSearchPassed = true;
        state.running = 'finish';
        state.reportLines = action.payload;
      })
      .addCase(getDispatchMainReport.rejected.type, (state, action) => {
        state.isSearchPassed = false;
        state.running = action.payload;
      });
  },
});

const selectDispatchMain = (state) => state.internationalMonitoring.dispatch.main;
export const selectForm = createSelector(selectDispatchMain, (main) => main.form);
export const selectSearchPassed = createSelector(selectDispatchMain, (main) => main.isSearchPassed);

export const { clearSearch, setForm } = mainSlice.actions;
export default mainSlice.reducer;
