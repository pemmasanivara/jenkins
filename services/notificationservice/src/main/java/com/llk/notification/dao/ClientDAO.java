package com.llk.notification.dao;

import java.util.Map;

import com.llk.common.model.Message;

public interface ClientDAO {
  Message getClientDetails(Integer clientId);
  void updateOutLookEventSendStatus(Integer clientId, Integer theraistId, Integer therapyId);
  void updateOutLookEventIds(Map<Long,String> evnstIds);
}
